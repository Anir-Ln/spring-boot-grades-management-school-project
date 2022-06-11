package com.gsnotes.bo;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represente un compte utilisateur
 * 
 * @author T. BOUDAA
 *
 */

@Entity
@Table(name="compte")
public class Compte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCompte;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean credentialsNonExpired = true;

	private boolean enabled = true;

	private String login;

	private String password;

	private boolean disconnectAccount;

	private boolean accepteDemandeAutorisationAbsence;

	private boolean affichePhoto;

	private boolean afficheEmail;

	@ManyToOne
	@JoinColumn(name = "idRole")
	private Role role;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idUtilisateur")
	private Utilisateur proprietaire;

	@Override
	public String toString() {
		return "Compte [idCompte=" + idCompte + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked="
				+ accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled
				+ ", login=" + login + ", password=" + password + ", disconnectAccount=" + disconnectAccount
				+ ", accepteDemandeAutorisationAbsence=" + accepteDemandeAutorisationAbsence + ", affichePhoto="
				+ affichePhoto + ", afficheEmail=" + afficheEmail + ", role=" + role + "]";
	}

	public Long getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(Long idCompte) {
		this.idCompte = idCompte;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDisconnectAccount() {
		return disconnectAccount;
	}

	public void setDisconnectAccount(boolean disconnectAccount) {
		this.disconnectAccount = disconnectAccount;
	}

	public boolean isAccepteDemandeAutorisationAbsence() {
		return accepteDemandeAutorisationAbsence;
	}

	public void setAccepteDemandeAutorisationAbsence(boolean accepteDemandeAutorisationAbsence) {
		this.accepteDemandeAutorisationAbsence = accepteDemandeAutorisationAbsence;
	}

	public boolean isAffichePhoto() {
		return affichePhoto;
	}

	public void setAffichePhoto(boolean affichePhoto) {
		this.affichePhoto = affichePhoto;
	}

	public boolean isAfficheEmail() {
		return afficheEmail;
	}

	public void setAfficheEmail(boolean afficheEmail) {
		this.afficheEmail = afficheEmail;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Utilisateur getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}

}