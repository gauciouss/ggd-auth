package ggd.auth.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Adm_User")
public class AdmUser implements Serializable {

	private static final long serialVersionUID = 5669246502073898207L;

	@Id
	@Column(name = "account")
	private String account;
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "tel")
	private String tel;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "create_date")
	private Timestamp createDate;
	
	@Column(name = "update_date")
	private Timestamp updateDate;
	
	@Column(name = "isEnabled")
	private boolean isEnabled;
	
	@Column(name = "isApproved")
	private boolean isApproved;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AdmGroup group;
	
	@Transient
	private List<AdmFunc> funcs;
	
	
	public AdmUser() {}


	public AdmUser(String account, String pwd, String email, String address, String tel, String phone,
			Timestamp createDate, Timestamp updateDate, boolean isEnabled, boolean isApproved, AdmGroup group) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.email = email;
		this.address = address;
		this.tel = tel;
		this.phone = phone;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isEnabled = isEnabled;
		this.isApproved = isApproved;
		this.group = group;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdmUser [account=");
		builder.append(account);
		builder.append(", pwd=");
		builder.append(pwd);
		builder.append(", email=");
		builder.append(email);
		builder.append(", address=");
		builder.append(address);
		builder.append(", tel=");
		builder.append(tel);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", isApproved=");
		builder.append(isApproved);
		builder.append(", group=");
		builder.append(group);
		builder.append(", funcs=");
		builder.append(funcs);
		builder.append("]");
		return builder.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdmUser other = (AdmUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	
	
}
