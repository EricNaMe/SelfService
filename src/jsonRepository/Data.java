/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonRepository;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "idDoc",
    "descripcion",
    "nombre",
    "fecha",
    "eliminar"
})
/**
 *
 * @author oc333
 */
public class Data {

    @JsonProperty("idDoc")
    private String idDoc;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("fecha")
    private String fecha;
    @JsonProperty("eliminar")
    private String eliminar;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The idDoc
     */
    @JsonProperty("idDoc")
    public String getIdDoc() {
        return idDoc;
    }

    /**
     *
     * @param idDoc The idDoc
     */
    @JsonProperty("idDoc")
    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    /**
     *
     * @return The descripcion
     */
    @JsonProperty("descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion The descripcion
     */
    @JsonProperty("descripcion")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return The nombre
     */
    @JsonProperty("nombre")
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre The nombre
     */
    @JsonProperty("nombre")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
    *
    * @return The fecha
    */
   @JsonProperty("fecha")
   public String getFecha() {
       return fecha;
   }

   /**
    *
    * @param nombre The fecha
    */
   @JsonProperty("fecha")
   public void setFecha(String fecha) {
       this.fecha = fecha;
   }
   
   /**
   *
   * @return The eliminar
   */
  @JsonProperty("eliminar")
  public String getEliminar() {
      return eliminar;
  }

  /**
   *
   * @param nombre The eliminar
   */
  @JsonProperty("eliminar")
  public void setEliminar(String eliminar) {
      this.eliminar = eliminar;
  }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
