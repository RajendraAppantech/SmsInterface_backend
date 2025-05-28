package com.sms.repositories;

import com.sms.entity.BankNameMaster;
import com.sms.entity.MccCodeMaster;
import com.sms.entity.PasswordHistory;
import com.sms.entity.PincodeMaster;
import com.sms.entity.SmsMaster;
import com.sms.entity.UserMaster;
import com.sms.entity.UserMenu;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class Repositories {
   public interface BankNameMasterRepository extends JpaRepository<BankNameMaster, String> {
   }

   public interface MccCodeMasterRepository extends JpaRepository<MccCodeMaster, String> {
      List<MccCodeMaster> findByMcc(String mcCocde);
   }

   public interface PasswordExpRepository extends JpaRepository<PasswordHistory, String> {
      @Query(
         value = "SELECT * FROM passwd_history WHERE user_id = :userId ORDER BY change_time DESC LIMIT 3",
         nativeQuery = true
      )
      List<PasswordHistory> fetchPassword(@Param("userId") String userId);

      @Query(
         value = "SELECT * FROM passwd_history WHERE user_id = :userId AND old_passwd = :newpass ORDER BY change_time DESC LIMIT 3",
         nativeQuery = true
      )
      List<PasswordHistory> fetchPasswordAndOldPasswd(@Param("userId") String userId, @Param("newpass") String newpass);
   }

   public interface PincodeMasterRepository extends JpaRepository<PincodeMaster, String> {
      List<PincodeMaster> findByPinCode(String pincode);
   }

   public interface SmsMasterRepository extends JpaRepository<SmsMaster, Long> {
      SmsMaster findByMobileNoAndOtp(String mobileNo, String otp);

      SmsMaster findTop1ByOrderBySmsidDesc();

      SmsMaster findByUsernameAndOtp(String username, String otp);
   }

   public interface UserMasterRepository extends JpaRepository<UserMaster, String> {
      UserMaster findByUserId(String userId);

      UserMaster findByMobileNo(String mobileNo);

      Page<UserMaster> findAll(Specification<UserMaster> specification, Pageable paging);

      @Transactional
      @Modifying
      @Query(
         value = "UPDATE user_master SET passwd=:passwd, passwd_exp=:passwdExp, auth_status='1' WHERE user_id = :userId",
         nativeQuery = true
      )
      int updatePasswordMaster(@Param("passwd") String passwd, @Param("passwdExp") Date passwdExp, @Param("userId") String userId);

      @Transactional
      @Modifying
      @Query(
         value = "UPDATE user_master SET logout_status=:logoutStatus, last_login_dt=:lastLoginDt WHERE user_id = :userId",
         nativeQuery = true
      )
      int updateLoginDetailsMaster(@Param("logoutStatus") String logoutStatus, @Param("lastLoginDt") Date lastLoginDt, @Param("userId") String userId);

      @Transactional
      @Modifying
      @Query(
         value = "UPDATE user_master SET logout_status=:logoutStatus, last_logout_date=:lastLogoutDate WHERE user_id = :userId",
         nativeQuery = true
      )
      int updateLogOutDetailsMaster(@Param("logoutStatus") String logoutStatus, @Param("lastLogoutDate") Date lastLogoutDate, @Param("userId") String userId);

      @Transactional
      @Modifying
      @Query(
         value = "UPDATE user_master SET login_attempt=:loginAttempt, lock_time=:lockTime, last_login_dt=:lastLoginDt WHERE user_id = :userId",
         nativeQuery = true
      )
      int updateUnloackPeroid(@Param("loginAttempt") Integer loginAttempt, @Param("lockTime") Date lockTime, @Param("lastLoginDt") Date lastLoginDt, @Param("userId") String userId);

      @Transactional
      @Modifying
      @Query(
         value = "UPDATE user_master SET login_attempt=:loginAttempt, lock_time=:lockTime WHERE user_id = :userId",
         nativeQuery = true
      )
      int updateLockingPeroid(@Param("loginAttempt") Integer loginAttempt, @Param("lockTime") Date lockTime, @Param("userId") String userId);
   }

   public interface UserMenuRepository extends JpaRepository<UserMenu, Long> {
      UserMenu findByUserRole(String userRole);
   }
}
