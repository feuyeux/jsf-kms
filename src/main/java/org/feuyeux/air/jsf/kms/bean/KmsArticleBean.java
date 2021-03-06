package org.feuyeux.air.jsf.kms.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.feuyeux.air.jsf.kms.dao.exceptions.PreexistingEntityException;
import org.feuyeux.air.jsf.kms.dao.jpa2.KmsArticleDao;
import org.feuyeux.air.jsf.kms.dao.jpa2.KmsKnowledgeDao;
import org.feuyeux.air.jsf.kms.dao.util.KmsPrimaryKey;
import org.feuyeux.air.jsf.kms.entity.KmsArticle;
import org.feuyeux.air.jsf.kms.entity.KmsKnowledge;
import org.feuyeux.air.jsf.kms.entity.KmsUser;
import org.feuyeux.air.jsf.kms.util.FileUploadBean;
import org.feuyeux.air.jsf.kms.util.KMSFile;

/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
@ManagedBean
@ViewScoped
public class KmsArticleBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static String attachment_separator = ";";
    private KmsArticleDao dao;
    private KmsKnowledgeDao kmsKnowledgeDao;
    private KmsArticle kmsArticle;// support TinyMCE
    private List<KmsArticle> kmsArticleItems;
    private String kmsKnowledgeId;
    private FileUploadBean uploader;

    public KmsArticleBean() {
        dao = new KmsArticleDao();
        kmsKnowledgeDao = new KmsKnowledgeDao();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String aId = params.get("aId");

        if (aId != null) {
            kmsArticle = dao.findKmsArticle(aId);
            kmsKnowledgeId = kmsArticle.getKmsKnowledge().getKnowledgeId();
        } else {
            kmsKnowledgeId = params.get("kId");// maybe null.
            kmsArticle = new KmsArticle();
        }

        uploader = new FileUploadBean();
    }

    public FileUploadBean getUploader() {
        return uploader;
    }

    public String create() throws IOException {
        KmsKnowledge kmsKnowledge = kmsKnowledgeDao.findKmsKnowledge(kmsKnowledgeId);
        if (kmsKnowledge == null) {
            return "kmsArticle_create";
        }
        kmsArticle.setKmsKnowledge(kmsKnowledge);

        boolean attachmentOK = true;
        StringBuilder attachments = null;

        if (uploader.getFiles().size() > 0) {
            attachments = new StringBuilder();
        }
        final String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "uploadFiles";
        for (int i = 0; i < uploader.getFiles().size(); i++) {
            KMSFile kmsFile = uploader.getFiles().get(i);
            String fileName = kmsFile.getName();
            File file = new File(filePath, fileName);
            FileOutputStream out;

            out = new FileOutputStream(file);
            out.write(kmsFile.getData());

            attachments.append(fileName);

            if (i < uploader.getFiles().size() - 1) {
                attachments.append(attachment_separator);
            }
        }

        try {
            if (attachmentOK && attachments != null) {
                kmsArticle.setAttachment(attachments.toString());
            }

            String articleId = KmsPrimaryKey.ARITICLE + System.nanoTime();
            kmsArticle.setArticleId(articleId);

            Map<String, Object> sesions = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            KmsUser kmsUser = (KmsUser) sesions.get("kmsUser");
            kmsArticle.setKmsUser(kmsUser);

            dao.create(kmsArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "kmsArticle_list?faces-redirect=true";
    }

    public String update() throws IOException {
        KmsKnowledge kmsKnowledge = kmsKnowledgeDao.findKmsKnowledge(kmsKnowledgeId);
        if (kmsKnowledge == null) {
            return "kmsArticle_edit";
        }
        kmsArticle.setKmsKnowledge(kmsKnowledge);

        boolean attachmentOK = true;
        StringBuilder attachments = null;

        if (uploader.getFiles().size() > 0) {
            attachments = new StringBuilder();
            attachments.append(kmsArticle.getAttachment());
            attachments.append(attachment_separator);
            final String filePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "uploadFiles";
            for (int i = 0; i < uploader.getFiles().size(); i++) {
                KMSFile kmsFile = uploader.getFiles().get(i);
                String fileName = kmsFile.getName();
                File file = new File(filePath, fileName);
                FileOutputStream out;

                out = new FileOutputStream(file);
                out.write(kmsFile.getData());

                attachments.append(fileName);

                if (i < uploader.getFiles().size() - 1) {
                    attachments.append(attachment_separator);
                }
            }
            if (attachmentOK) {
                kmsArticle.setAttachment(attachments.toString());
            }
        }
        try {
            dao.edit(kmsArticle);
        } catch (PreexistingEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "kmsArticle_list?faces-redirect=true";

    }

    public List<KmsArticle> getKmsArticleItems() {
        if (kmsArticleItems == null) {
            if (kmsKnowledgeId != null) {
                searchByKey();
                return kmsArticleItems;
            }
            kmsArticleItems = dao.findKmsArticleEntities();
        }
        return kmsArticleItems;
    }

    public KmsArticle getKmsArticle() {
        return kmsArticle;
    }

    public String getKmsKnowledgeId() {
        return kmsKnowledgeId;
    }

    public ArrayList<String> getAttachments() {
        String kmspath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/uploadFiles/";
        String attachmentParameter = kmsArticle.getAttachment();
        if (attachmentParameter != null && attachmentParameter.length() > 0) {
            String[] attachments = attachmentParameter.split(attachment_separator);
            ArrayList<String> result = new ArrayList<String>();
            for (String attachment : attachments) {
                result.add(kmspath + attachment);
            }
            return result;
        } else {
            return null;
        }
    }

    public void setKmsKnowledgeId(String kmsKnowledgeId) {
        this.kmsKnowledgeId = kmsKnowledgeId;
    }

    public boolean isShow() {
        kmsArticleItems = getKmsArticleItems();
        return kmsArticleItems != null && kmsArticleItems.size() > 0;
    }

    public void searchByKey() {
        KmsKnowledge kmsKnowledge = kmsKnowledgeDao.findKmsKnowledge(kmsKnowledgeId);
        if (kmsKnowledge == null) {
            return;
        }
        kmsArticleItems = dao.searchKmsArticle(kmsKnowledge);
    }
}
