/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restrw.Credentials;

/**
 *
 * @author seaph
 */
@Stateless
@Path("restrw.credentials")
public class CredentialsFacadeREST extends AbstractFacade<Credentials> {

    @PersistenceContext(unitName = "M3PU")
    private EntityManager em;

    public CredentialsFacadeREST() {
        super(Credentials.class);
    }

    //@POST
    //@Override
   // @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    //public void create(Credentials entity) {
    //    super.create(entity);
    //}
    
    @POST
    @Path("Credentials.signUp")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credentials signUp(Credentials entity) {
        String newName = entity.getUsername();
        if (findByUsername(newName).isEmpty()){
            super.create(entity);
            List<Credentials>  listResult = findByUsername(newName);
            return listResult.get(0);
        }
        return null;
    }

      @GET 
    @Path("Credentials.findByUsername/{username}")     
    @Produces({"application/json"})     
    public List<Credentials> findByUsername(@PathParam("username") String username) 
    {         
        Query query = em.createNamedQuery("Credentials.findByUsername");         
        query.setParameter("username", username);         
        return query.getResultList();     
    }

        @GET     
    @Path("Credentials.findByUsernamePassword/{username}/{password}")     
    @Produces({"application/json"})     
    public List<Credentials> findByUsernamePassword(@PathParam("username") String username, @PathParam("password") String password) 
    {         
        TypedQuery<Credentials> query = em.createQuery("SELECT c FROM Credentials c WHERE c.username = :username AND c.password = :password", Credentials.class);         
        query.setParameter("username",username);
        query.setParameter("password",password);

        return query.getResultList();     
}

    

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credentials entity) {
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
    public Credentials find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credentials> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
}
