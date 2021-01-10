package pp.inzynierka.douslug.data

import pp.inzynierka.douslug.db.DBController
import pp.inzynierka.douslug.model.appUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<appUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = appUser("test@example.com", "Golarz Filip")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}