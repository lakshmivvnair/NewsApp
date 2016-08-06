package com.example.android.newsapp;

/**
 * Created by lakshmivineeth on 8/6/16.
 */
class News {

    private String mSectionName;
    private String mWebPublicationDate;
    private String mWebTitle;
    private String mWebUrl;
    private String mContributor;

    public News(String sectionName, String date, String title, String url, String contributor) {
        this.setSectionName(sectionName);
        this.setWebPublicationDate(date);
        this.setWebTitle(title);
        this.setWebUrl(url);
        this.setContributor(contributor);
    }

    public String getSectionName() {
        return mSectionName;
    }

    private void setSectionName(String mSectionName) {
        this.mSectionName = mSectionName;
    }

    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    private void setWebPublicationDate(String webPublicationDate) {
        this.mWebPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    private void setWebTitle(String webTitle) {
        this.mWebTitle = webTitle;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    private void setWebUrl(String webUrl) {
        this.mWebUrl = webUrl;
    }

    public String getContributor() {
        return mContributor;
    }

    private void setContributor(String mContributor) {
        this.mContributor = mContributor;
    }
}
