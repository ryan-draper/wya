package backend.wya.model;

public class Result {

    private Boolean isFlagged;
    private String landMark;
    private String text;
    private String imagePath = "/Users/pasansirithanachai/repositories/hackrice12/wya/backend/wya/result";

    public Result(Boolean isFlagged, String landMark, String text, String imagePath) {
        this.isFlagged = isFlagged;
        this.landMark = landMark;
        this.text = text;
        this.imagePath = imagePath;
    }

    public Result(){
    }

    public Boolean getFlagged() {
        return isFlagged;
    }

    public void setFlagged(Boolean flagged) {
        isFlagged = flagged;
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
}
