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
	public AdmUser authenticate(String account, String pwd) throws AuthException;
	
	/**
	 * 查詢使用者
	 * @return
	 */
	public List<AdmUser> findUsers() throws AuthException; 
	
	
	/**
	 * 查詢使用者
	 * @param page
	 * @param row
	 * @return
	 */
	public List<AdmUser> findUsers(int page, int row) throws AuthException; 
	
	/**
	 * 查詢使用者
	 * @param id
	 * @return
	 */
	public AdmUser findUserById(String id) throws AuthException;
	
	/**
	 * 查詢使用者
	 * @param value account or name
	 * @return
	 */
	public List<AdmUser> findUsers(String value) throws AuthException;
	
	/**
	 * 新增使用者
	 * @param user
	 */
	public void addUser(AdmUser user) throws AuthException;
	
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
	public void updateUser(String account, String password, String name, String email, String address, String tel, String phone, String groupId) throws AuthException;
	
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
	 * @param isEnabled
	 * @param isApproved
	 */
	public void updateUser(String account, String password, String name, String email, String address, String tel, String phone, String groupId, boolean isEnabled, boolean isApproved) throws AuthException;
	
	/**
	 * 啟用使用者	
	 * @param account
	 */
	public void enableUser(String account) throws AuthException;
	
	/**
	 * 停用使用者	
	 * @param account
	 */
	public void disableUser(String account) throws AuthException;
	
	/**
	 * 允許開放使用者
	 * @param account
	 */
	public void approveUser(String account) throws AuthException;
	
	/**
	 * 不允許開放使用者
	 * @param account
	 */
	public void unApproveUser(String account) throws AuthException;
	
	/**
	 * 新增群組
	 * @param grpName
	 */
	public String addGroup(String grpName, boolean isManager, boolean isEnabled, boolean isApproved) throws AuthException;
	
	/**
	 * 更新群組資訊
	 * @param grpId
	 * @param grpName
	 * @param isEnabled
	 * @param isApproved
	 */
	public void updateGroup(String grpId, String grpName, boolean isEnabled, boolean isApproved) throws AuthException;
	
	/**
	 * 啟用群組
	 * @param grpId
	 */
	public void enableGroup(String grpId) throws AuthException;
	
	/**
	 * 停用群組
	 * @param grpId
	 */
	public void disableGroup(String grpId) throws AuthException;
	
	/**
	 * 允許開通群組
	 * @param grpId
	 */
	public void approveGroup(String grpId) throws AuthException;
	
	/**
	 * 不允許開通群組
	 * @param grpId
	 */
	public void unApproveGroup(String grpId) throws AuthException;

	/**
	 * 查詢所有group
	 * @param isEnabled
	 * @param isApproved
	 */
	public List<AdmGroup> findAllGroup(boolean isEnabled, boolean isApproved) throws AuthException;
	
	/**
	 * 查詢所有group
	 * @param isEnabled
	 * @param isApproved
	 */
	public List<AdmGroup> findAllGroup() throws AuthException;
	
	/**
	 * 查詢group
	 * @param id
	 * @return
	 */
	public AdmGroup findGroup(String id) throws AuthException;
	
	/********************************************************************************* edit function ************************************************************************************/
	
	/**
	 * 查詢所有功能
	 */
	public List<AdmFunc> findAllFunc(boolean rootOnly) throws AuthException;
	
	/**
	 * 查詢所有可執行功能功能
	 */
	public List<AdmFunc> findAllApprovedFunc() throws AuthException;
	/**
	 * 查詢特定功能
	 * @param id
	 */
	public AdmFunc findFuncById(String id) throws AuthException;
	
	/**
	 * 新增功能
	 * @param func
	 */
	public void addFunc(String funcName, boolean isRoot, String parentId, String url, int sort, boolean isEnabled, boolean isApproved) throws AuthException;
	
	/**
	 * 更新功能資訊
	 * @param func
	 */
	public void updateFunc(String func_id, String funcName, boolean isRoot, String parentId, String url, int sort, boolean isEnabled, boolean isApproved) throws AuthException;
	
	/**
	 * 啟用功能
	 * @param funcid
	 * @param isEnable
	 */
	public void enableFunc(String funcid, boolean isEnable) throws AuthException;
	
	/**
	 * 允許功能
	 * @param funcid
	 * @param isApprove
	 */
	public void approveFunc(String funcid, boolean isApprove) throws AuthException;
	
	/**
	 * 把使用者設定到群組中
	 * @param account
	 * @param groupId
	 */
	public void addUser2Group(String account, String groupId) throws AuthException;
	
	/**
	 * 把使用者移出群組
	 * @param account
	 * @param groupId
	 */
	public void removeUserFromGroup(String account, String groupId) throws AuthException;
	
	/**
	 * 把功能加入到群組中
	 * @param funcId
	 * @param groupId
	 */
	public void addFunc2Group(String groupId, List<String> funcs) throws AuthException;
	
	/**
	 * 把功能移出群組中
	 * @param funcId
	 * @param groupId
	 */
	public void removeFuncFromGroup(String funcId, String groupId) throws AuthException;
	
}
