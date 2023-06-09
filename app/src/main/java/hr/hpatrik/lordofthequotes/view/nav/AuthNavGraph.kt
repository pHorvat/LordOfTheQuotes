package hr.hpatrik.lordofthequotes.view.nav

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import hr.hpatrik.lordofthequotes.R
import hr.hpatrik.lordofthequotes.view.AuthScreen
import hr.hpatrik.lordofthequotes.view.auth.AuthenticationScreen
import hr.hpatrik.lordofthequotes.viewmodel.AuthenticationViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel,
    context: Context
) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Login.route
    ) {


        composable(route = AuthScreen.Login.route) {
            AuthenticationScreen(
                icon = R.drawable.login,
                onLogin = {
                    authenticationViewModel.logIn(
                        onSuccess = {
                            navController.popBackStack()
                            navController.navigate(Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    )

                },
                logInAnon = {
                    authenticationViewModel.logInAnon(
                        onSuccess = {
                            navController.popBackStack()
                            navController.navigate(Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    )

                },
                onRegister = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.Register.route)
                },
                authenticationState = authenticationViewModel.authenticationState.value,
                onEmailChanged = { authenticationViewModel.onEmailChanged(it) },
                onPasswordChanged = { authenticationViewModel.onPasswordChanged(it) }
            )
        }
        composable(route = AuthScreen.Register.route) {
            AuthenticationScreen(
                icon = R.drawable.register,
                onLogin = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.Login.route)
                },
                logInAnon = {
                    authenticationViewModel.logInAnon(
                        onSuccess = {
                            navController.popBackStack()
                            navController.navigate(Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    )

                },
                onRegister = {
                    authenticationViewModel.register(
                        onSuccess = {
                            navController.popBackStack()
                            navController.navigate(Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                authenticationState = authenticationViewModel.authenticationState.value,
                onEmailChanged = { authenticationViewModel.onEmailChanged(it) },
                onPasswordChanged = { authenticationViewModel.onPasswordChanged(it) },
                isLogin = false
            )
        }
    }
}