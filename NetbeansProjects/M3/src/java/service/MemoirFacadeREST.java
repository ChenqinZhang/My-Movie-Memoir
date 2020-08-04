/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restrw.Memoir;

/**
 *
 * @author seaph
 */
@Stateless
@Path("restrw.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "M3PU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("Memoir.findByIdFORTopFive/{personId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findByIdFORTopFive(@PathParam("personId") Integer personId)
    {
        Integer recentYears = Calendar.getInstance().get(Calendar.YEAR);
        
       Query query = em.createQuery("SELECT m.movieName, m.movieReleasedD, m.ratingScore FROM Memoir m "
            + "WHERE m.personId.personId = :personId "
            + "AND EXTRACT(YEAR m.movieWatchedD) = :recentYears "
            + "ORDER BY m.ratingScore DESC");      
            
        query.setParameter("personId", personId);    
        query.setParameter("recentYears", recentYears); 
        
        List<Object[]> queryList = query.setMaxResults(5).getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] row : queryList) {             
                JsonObject personObject = Json.createObjectBuilder().                          
                        add("movieName",(String)row[0] )                          
                        .add("movieReleasedD", simpleDateFormat.format((Date)row[1]))
                        .add("ratingScore", (BigDecimal)row[2] ).build();
                        arrayBuilder.add(personObject);        
            }        
            JsonArray jArray = arrayBuilder.build();        
            return jArray; 
    }
    @GET 
    @Path("Memoir.findByPersonId/{personId}")     
    @Produces({"application/json"})     
    public List<Memoir> findByPersonId(@PathParam("personId") Integer personId) 
    {         
        Query query = em.createNamedQuery("Memoir.findByPersonId");         
        query.setParameter("personId", personId);   
        return query.getResultList();     
    }

    @GET
    @Path("Memoir.findByIdANDPeriod/{personId}/{start_Date}/{end_Date}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findByIdANDPeriod(@PathParam("personId") Integer personId, @PathParam("start_Date") String start_Date, @PathParam("end_Date") String end_Date) 
{

        Date startDate = new Date(Long.parseLong(start_Date));
        Date endDate = new Date(Long.parseLong(end_Date));  
      
  Query query = em.createQuery("SELECT m.cinemaId.cinemaPostcode, COUNT(m.movieName) FROM Memoir m "
                + "WHERE m.personId.personId = :personId "
                + "AND m.movieWatchedD >= :startDate "
                + "AND m.movieWatchedD <= :endDate "
                + "GROUP BY m.cinemaId.cinemaPostcode");         
        query.setParameter("personId", personId);   
        query.setParameter("startDate", startDate); 
        query.setParameter("endDate", endDate); 
        
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
            for (Object[] row : queryList) {             
                JsonObject personObject = Json.createObjectBuilder().                          
                        add("cinemaPostCode", (String)row[0])                          
                        .add("countOfMovies", (Long)row[1]).build();
                         arrayBuilder.add(personObject);        
            }        
            JsonArray jArray = arrayBuilder.build();        
            return jArray; 
}
      @GET
    @Path("Memoir.findByIdANDYear/{personId}/{year}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findByIdANDYear(@PathParam("personId") Integer personId, @PathParam("year") Integer year)
    {
          Query query = em.createQuery("SELECT EXTRACT(MONTH m.movieWatchedD), COUNT(m.movieName) FROM Memoir m "
                + "WHERE m.personId.personId = :personId "
                + "GROUP BY EXTRACT(MONTH m.movieWatchedD) "
                + "HAVING EXTRACT(MONTH m.movieWatchedD) "
                + "IN (SELECT EXTRACT(MONTH m.movieWatchedD) FROM Memoir m WHERE m.personId.personId = :personId AND EXTRACT(YEAR m.movieWatchedD) = :year)");      
            
        query.setParameter("personId", personId);    
        query.setParameter("year", year); 
        
        List<Object[]> queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
            for (Object[] row : queryList) {             
                JsonObject personObject = Json.createObjectBuilder().                          
                  add("month",new DateFormatSymbols().getMonths()[(Integer)row[0]-1])                          
                  .add("countOfMovies", (Long)row[1]).build();
                  arrayBuilder.add(personObject);        
            }        
            JsonArray jArray = arrayBuilder.build();        
            return jArray; 
}     



}
