/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package news;

/**
 *
 * @author alexander
 */
public class News {

    public int idNews;
    public String tittle;
    public int typeNews;
    public String details;
    public String urlImage;
    public String dateBegin;
    public String dateEnd;

    public News() {
    }

    public int getIdNews() {
        return idNews;
    }

    public void setIdNews(int idNews) {
        this.idNews = idNews;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getTypeNews() {
        return typeNews;
    }

    public void setTypeNews(int typeNews) {
        this.typeNews = typeNews;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}