package pp.inzynierka.douslug.data

import org.mindrot.jbcrypt.BCrypt
import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.appUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<appUser> {
        try {
            val loginUser = DBController.findUserByEmail(username)
            if (loginUser == null || !BCrypt.checkpw(password, loginUser.password)) {
                throw Exception("Złe dane logowania")
            }
            return Result.Success(loginUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}