package krunal.com.example.newsapp;

/**
 * Created by acer on 05-03-2018.
 */

public class News {

    private String mWebTitle;
    private String mType;
    private String mDate;
    private String mSectionName;
    private String mUrl;

    public News(){
    }

    News(String mWebTitle, String mType, String mDate, String mSectionName, String mUrl) {
        this.mWebTitle = mWebTitle;
        this.mType = mType;
        this.mDate = mDate;
        this.mSectionName = mSectionName;
        this.mUrl = mUrl;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmType() {
        return mType;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "names='" + mWebTitle + '\'' +
                ", age='" + mUrl + '\'' +
                '}';
    }


}
