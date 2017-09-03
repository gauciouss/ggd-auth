package ggd.auth.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Adm_Func")
public class AdmFunc implements Serializable {

	private static final long serialVersionUID = 1177345812549686781L;

	@Id
	@Column(name = "func_id")
	private String funcId;
	
	@Column(name = "func_name")
	private String funcName;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private AdmFunc parent;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parent")	
	private Set<AdmFunc> subs;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "adm_group_func_map", joinColumns = {
			@JoinColumn(name = "func_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "group_id",
					nullable = false, updatable = false) })
	private Set<AdmGroup> groups;
	
	@Column(name = "is_root")
	private boolean isRoot;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "sort")
	private Integer sort;
	
	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "isEnabled")
	private boolean isEnabled;

	@Column(name = "isApproved")
	private boolean isApproved;

	public Set<AdmGroup> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<AdmGroup> groups) {
		this.groups = groups;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public Set<AdmFunc> getSubs() {
		return subs;
	}

	public void setSubs(Set<AdmFunc> subs) {
		this.subs = subs;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
	
	public AdmFunc getParent() {
		return parent;
	}
	
	public void setParent(AdmFunc parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdmFunc [funcId=");
		builder.append(funcId);
		builder.append(", funcName=");
		builder.append(funcName);
		builder.append(", isRoot=");
		builder.append(isRoot);
		builder.append(", url=");
		builder.append(url);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", isApproved=");
		builder.append(isApproved);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
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
		AdmFunc other = (AdmFunc) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		return true;
	}
	
	
}