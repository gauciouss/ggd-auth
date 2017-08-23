package ggd.auth.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "adm_group")
public class AdmGroup implements Serializable {

	private static final long serialVersionUID = -726106176913853226L;

	@Id
	@Column(name = "group_id")
	private String groupId;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;

	@Column(name = "isEnabled")
	private boolean isEnabled;

	@Column(name = "isApproved")
	private boolean isApproved;

	@Transient
	private List<AdmFunc> funcs;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "group")
	private Set<AdmUser> users;

	public AdmGroup() {
	}

	public AdmGroup(String groupId, String groupName, Timestamp createDate, Timestamp updateDate, boolean isEnabled,
			boolean isApproved) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.isEnabled = isEnabled;
		this.isApproved = isApproved;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Set<AdmUser> getUsers() {
		return users;
	}

	public void setUsers(Set<AdmUser> users) {
		this.users = users;
	}

	public List<AdmFunc> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<AdmFunc> funcs) {
		this.funcs = funcs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdmGroup [groupId=");
		builder.append(groupId);
		builder.append(", groupName=");
		builder.append(groupName);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", isEnabled=");
		builder.append(isEnabled);
		builder.append(", isApproved=");
		builder.append(isApproved);
		builder.append(", funcs=");
		builder.append(funcs);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		AdmGroup other = (AdmGroup) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

}
