package ggd.auth.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "adm_user")
public class AdmUser implements Serializable {

	private static final long serialVersionUID = 5669246502073898207L;

	@Id
	@Column(name = "account")
	private String account;
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "name")
	private String name;
	
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id", insertable = false, updatable = false, unique = true)
	private AdmGroup group;
	
	@Transient
	private List<AdmFunc> funcs;
	
	
	public AdmUser() {}


	public AdmUser(String account, String pwd, String name, String email, String address, String tel, String phone,
			Timestamp createDate, Timestamp updateDate, boolean isEnabled, boolean isApproved, AdmGroup group) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.name = name;
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
	
	


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	public Timestamp getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}


	public boolean isEnabled() {
		return isEnabled;
	}


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	public boolean isApproved() {
		return isApproved;
	}


	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}


	public AdmGroup getGroup() {
		return group;
	}


	public void setGroup(AdmGroup group) {
		this.group = group;
	}


	public List<AdmFunc> getFuncs() {
		return funcs;
	}


	public void setFuncs(List<AdmFunc> funcs) {
		this.funcs = funcs;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdmUser [account=");
		builder.append(account);
		builder.append(", pwd=");
		builder.append(pwd);
		builder.append(", name=");
		builder.append(name);
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
