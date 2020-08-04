/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restrw;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seaph
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findByMemoirId", query = "SELECT m FROM Memoir m WHERE m.memoirId = :memoirId")
    , @NamedQuery(name = "Memoir.findByMovieName", query = "SELECT m FROM Memoir m WHERE m.movieName = :movieName")
    , @NamedQuery(name = "Memoir.findByMovieReleasedD", query = "SELECT m FROM Memoir m WHERE m.movieReleasedD = :movieReleasedD")
    , @NamedQuery(name = "Memoir.findByMovieWatchedD", query = "SELECT m FROM Memoir m WHERE m.movieWatchedD = :movieWatchedD")
    , @NamedQuery(name = "Memoir.findByMovieComment", query = "SELECT m FROM Memoir m WHERE m.movieComment = :movieComment")
    , @NamedQuery(name = "Memoir.findByRatingScore", query = "SELECT m FROM Memoir m WHERE m.ratingScore = :ratingScore")
    , @NamedQuery(name = "Memoir.findByMovieTmdbid", query = "SELECT m FROM Memoir m WHERE m.movieTmdbid = :movieTmdbid")
, @NamedQuery(name = "Memoir.findByPersonId", query = "SELECT m FROM Memoir m WHERE m.personId.personId = :personId")
})
public class Memoir implements Serializable {

    @Size(max = 100)
    @Column(name = "MOVIE_PPOSTPATH")
    private String moviePpostpath;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MEMOIR_ID")
    private Integer memoirId;
    @Size(max = 200)
    @Column(name = "MOVIE_NAME")
    private String movieName;
    @Column(name = "MOVIE_RELEASED_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date movieReleasedD;
    @Column(name = "MOVIE_WATCHED_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date movieWatchedD;
    @Size(max = 250)
    @Column(name = "MOVIE_COMMENT")
    private String movieComment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RATING_SCORE")
    private BigDecimal ratingScore;
    @Column(name = "MOVIE_TMDBID")
    private Integer movieTmdbid;
    @JoinColumn(name = "CINEMA_ID", referencedColumnName = "CINEMA_ID")
    @ManyToOne
    private Cinema cinemaId;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne
    private Person personId;

    public Memoir() {
    }

    public Memoir(Integer memoirId) {
        this.memoirId = memoirId;
    }

    public Integer getMemoirId() {
        return memoirId;
    }

    public void setMemoirId(Integer memoirId) {
        this.memoirId = memoirId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieReleasedD() {
        return movieReleasedD;
    }

    public void setMovieReleasedD(Date movieReleasedD) {
        this.movieReleasedD = movieReleasedD;
    }

    public Date getMovieWatchedD() {
        return movieWatchedD;
    }

    public void setMovieWatchedD(Date movieWatchedD) {
        this.movieWatchedD = movieWatchedD;
    }

    public String getMovieComment() {
        return movieComment;
    }

    public void setMovieComment(String movieComment) {
        this.movieComment = movieComment;
    }

    public BigDecimal getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(BigDecimal ratingScore) {
        this.ratingScore = ratingScore;
    }

    public Integer getMovieTmdbid() {
        return movieTmdbid;
    }

    public void setMovieTmdbid(Integer movieTmdbid) {
        this.movieTmdbid = movieTmdbid;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinema cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memoirId != null ? memoirId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.memoirId == null && other.memoirId != null) || (this.memoirId != null && !this.memoirId.equals(other.memoirId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restrw.Memoir[ memoirId=" + memoirId + " ]";
    }

    public String getMoviePpostpath() {
        return moviePpostpath;
    }

    public void setMoviePpostpath(String moviePpostpath) {
        this.moviePpostpath = moviePpostpath;
    }
    
}
