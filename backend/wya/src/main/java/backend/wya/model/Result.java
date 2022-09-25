package backend.wya.model;

public class Result {

    private Boolean flagged;
    private String landMark;
    private String imagePath = "/Users/pasansirithanachai/repositories/hackrice12/wya/backend/wya/result";

    public Result(Boolean flagged, String landMark, String imagePath) {
        this.flagged = flagged;
        this.landMark = landMark;
        this.imagePath = imagePath;
    }

    public Result(){
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getImagePath() {
        return imagePath;
    }
}
