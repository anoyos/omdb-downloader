package net.sprava.omdb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is super class of all entities in the project. It contains technical
 * attributes of all entities.
 * 
 * @author Nikolay Koretskyy
 *
 */
@MappedSuperclass
public abstract class IdentityEntity {

	private String imdbId;
	private Date createdAt;
	private Date modifiedAt;

	@JsonProperty("imdbID")
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMDB_ID")
	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	@Column(name = "CREATED_AT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "MODIFIED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

}
