package ggd.auth;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;

@Transactional
public interface AuthService {

	/**
	 * 驗證帳號密碼
	 * @param account
	 * @param pwd
	 * @return
	 */	
	public AdmUser authenticate(String account, String pwd);
	
	
	/**
	 * 查詢使用者
	 * @param page
	 * @param row
	 * @return
	 */
	public List<AdmUser> findUsers(int page, int row); 
	
	/**
	 * 查詢使用者
	 * @param id
	 * @return
	 */
	public AdmUser findUserById(String id);
	
	/**
	 * 查詢使用者
	 * @param value account or name
	 * @return
	 */
	public List<AdmUser> findUsers(String value);
	
	/**
	 * 新增使用者
	 * @param user
	 */
	public void addUser(AdmUser user);
	
	/**
	 * 更新使用者
	 * @param account
	 * @param password
	 * @param name
	 * @param email
	 * @param address
	 * @param tel
	 * @param phone
	 * @param groupId
	 */
	public void updateUser(String account, String password, String name, String email, String address, String tel, String phone, String groupId);
	
	/**
	 * 啟用使用者	
	 * @param account
	 */
	public void enableUser(String account);
	
	/**
	 * 停用使用者	
	 * @param account
	 */
	public void disableUser(String account);
	
	/**
	 * 允許開放使用者
	 * @param account
	 */
	public void approveUser(String account);
	
	/**
	 * 不允許開放使用者
	 * @param account
	 */
	public void unApproveUser(String account);
	
	/**
	 * 新增群組
	 * @param grpName
	 */
	public void addGroup(String grpName);
	
	/**
	 * 更新群組資訊
	 * @param group
	 */
	public void updateGroup(AdmGroup group);
	
	/**
	 * 啟用群組
	 * @param grpId
	 */
	public void enableGroup(String grpId);
	
	/**
	 * 停用群組
	 * @param grpId
	 */
	public void disableGroup(String grpId);
	
	/**
	 * 允許開通群組
	 * @param grpId
	 */
	public void approveGroup(String grpId);
	
	/**
	 * 不允許開通群組
	 * @param grpId
	 */
	public void unApproveGroup(String grpId);

	/**
	 * 查詢所有group
	 * @param isEnabled
	 * @param isApproved
	 */
	public List<AdmGroup> findAllGroup(boolean isEnabled, boolean isApproved);
	
	/**
	 * 新增功能
	 * @param func
	 */
	public void addFunc(AdmFunc func);
	
	/**
	 * 更新功能資訊
	 * @param func
	 */
	public void updateFunc(AdmFunc func);
	
	/**
	 * 啟用功能
	 * @param funcid
	 * @param isEnable
	 */
	public void enableFunc(String funcid, boolean isEnable);
	
	/**
	 * 允許功能
	 * @param funcid
	 * @param isApprove
	 */
	public void approveFunc(String funcid, boolean isApprove);
	
	/**
	 * 把使用者設定到群組中
	 * @param account
	 * @param groupId
	 */
	public void addUser2Group(String account, String groupId);
	
	/**
	 * 把使用者移出群組
	 * @param account
	 * @param groupId
	 */
	public void removeUserFromGroup(String account, String groupId);
	
	/**
	 * 把功能加入到群組中
	 * @param funcId
	 * @param groupId
	 */
	public void addFunc2Group(String funcId, String groupId);
	
	/**
	 * 把功能移出群組中
	 * @param funcId
	 * @param groupId
	 */
	public void removeFuncFromGroup(String funcId, String groupId);
}
