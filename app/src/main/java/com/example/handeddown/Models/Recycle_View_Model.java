package com.example.handeddown.Models;


public class Recycle_View_Model {
    String image;
    String recipename;
    String recipedescprition;
    String uploadedby;
    String startId;

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRecipename() {
            return recipename;
        }

        public void setRecipename(String recipename) {
            this.recipename = recipename;
        }

        public String getRecipedescprition() {
            return recipedescprition;
        }

        public void setRecipedescprition(String recipedescprition) {
            this.recipedescprition = recipedescprition;
        }

        public String getUploadedby() {
            return uploadedby;
        }

        public void setUploadedby(String uploadedby) {
            this.uploadedby = uploadedby;
        }

}
