package vttp2022.miniproject.anythingapp.models;

public class Review {
    
    private String establishmentName;
    private String overallRating;
    private String authorRating;
    private String authorName;
    private String authorReview;
    private String time;
    private String profilePhotoUrl;

    public String getEstablishmentName() {
        return establishmentName;
    }
    public String getAuthorReview() {
        return authorReview;
    }
    public void setAuthorReview(String authorReview) {
        this.authorReview = authorReview;
    }
    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }
    public String getOverallRating() {
        return overallRating;
    }
    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }
    public String getAuthorRating() {
        return authorRating;
    }
    public void setAuthorRating(String userRating) {
        this.authorRating = userRating;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }
    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    

}
