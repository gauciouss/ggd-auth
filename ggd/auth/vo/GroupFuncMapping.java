package ggd.auth.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adm_group_func_map")
public class GroupFuncMapping implements Serializable {

	private static final long serialVersionUID = 382445576888545711L;

	@Id
	@Column(name = "group_id")
	private String groupId;

	@Id
	@Column(name = "func_id")
	private String funcId;

	public GroupFuncMapping() {
	}

	public GroupFuncMapping(String groupId, String funcId) {
		super();
		this.groupId = groupId;
		this.funcId = funcId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the funcId
	 */
	public String getFuncId() {
		return funcId;
	}

	/**
	 * @param funcId
	 *            the funcId to set
	 */
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupFuncMapping [groupId=");
		builder.append(groupId);
		builder.append(", funcId=");
		builder.append(funcId);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		GroupFuncMapping other = (GroupFuncMapping) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

}
