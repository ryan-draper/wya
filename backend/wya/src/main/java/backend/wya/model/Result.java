package backend.wya.model;

public class Result {

    private String landMark;
    private String text;
    private String imagePath;

    public Result(String landMark, String text, String imagePath) {
        this.landMark = landMark;
        this.text = text;
        this.imagePath = imagePath;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
