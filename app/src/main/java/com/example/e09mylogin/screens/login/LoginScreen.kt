package com.example.e09mylogin.screens.login

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e09mylogin.R
import com.example.e09mylogin.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    ){

    val showLoginForm = rememberSaveable {mutableStateOf(true) }


    // Facebook
    /*val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcherFb = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)) {
        // nothing to do. handled in FacebookCallback

        scope.launch {
            val tokenFB = AccessToken.getCurrentAccessToken()
            val credentialFB = tokenFB?.let { FacebookAuthProvider.getCredential(it.token)}
            if(credentialFB != null){
                viewModel.signInWithFacebook(credentialFB){
                    navController.navigate(Screens.HomeScreen.name)
                }
            }
        }
    }*/

    //GOOGLE
    // Este token se consigue en Firebase -> Proveedor de acceso -> Conf del SDK -> Id del cliente web
    val token = "979989901552-a0hojhqh3relj7hgkggf33oiq0mvodb8.apps.googleusercontent.com"
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredential(credential){
                navController.navigate(Screens.HomeScreen.name)
            }
        } catch (ex: Exception) {
            Log.d("MyLogin", "GoogleSignIn falló")
        }
    }

    BackHandler {
        navController.navigate(Screens.WelcomeScreen.name) {
            // Evita apilar múltiples pantallas de bienvenida
            popUpTo(Screens.WelcomeScreen.name) { inclusive = true }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2C2C2C), // Gris oscuro
                            Color(0xFF1C1C1C), // Gris casi negro
                            Color(0xFF000000)  // Negro
                        )
                    )
                )
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_spotify),
                contentDescription = "Spotify Logo",
                modifier = Modifier
                    .size(100.dp) // Ajusta el tamaño de la imagen según sea necesario
                    .padding(bottom = 20.dp) // Espacio entre la imagen y el texto
            )

            //si es true crea la cuenta si es false inicia sesion
            if(showLoginForm.value){
                Text(text = "Inicia sesion")
                UserForm(isCreateAccount = false){ email, password ->
                    Log.d("MyLogin", "Logueado con $email y $password")
                    viewModel.signInWithEmailAndPassword(
                        email,
                        password
                    ) { //pasamos email y pwd y la funcion que navega hacia home
                        navController.navigate(Screens.HomeScreen.name)
                    }

                }


            } else {

                Text(text = "Crear cuenta")
                UserForm(isCreateAccount = false){ email, password ->
                    Log.d("MyLogin", "Logueado con $email y $password")
                    viewModel.createUserWithEmailAndPassword(
                        email,
                        password
                    ) { //pasamos email y pwd y la funcion que navega hacia home
                        navController.navigate(Screens.HomeScreen.name)
                    }

                }

            }

            //alternar entre Crear cuenta e inicia sesion
            Spacer(modifier = Modifier.height(15.dp))
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 = if (showLoginForm.value) "¿No tienes cuenta? "
                else "Ya tienes cuenta? "
                val text2 = if (showLoginForm.value) "Regístrate"
                else "Inicia sesión"
                Text(text = text1,
                    color = Color.White)
                Text(text = text2,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                    color = Color.White
                )

            }

            //GOOGLE
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(10.dp)
                    .padding(top = 100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(width = 2.dp, color = Color.White)
                    .clickable {
                        //se crea un builder de opciones, una de ellas inluye el token
                        val opciones = GoogleSignInOptions
                            .Builder(
                            GoogleSignInOptions.DEFAULT_SIGN_IN
                        )
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                        val googleSignInCliente = GoogleSignIn.getClient(context, opciones)
                        launcher.launch(googleSignInCliente.signInIntent)
                    },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = "Login con Google",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(15.dp),
                    text = "Login con Google",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            // FACEBOOK
            /*  Row(
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(10.dp)
                      .clip(RoundedCornerShape(10.dp))
                      .clickable {

                          launcherFb.launch(listOf("email","public_profile"))
                      },
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Image(
                      painter = painterResource(id = R.drawable.ic_fb),
                      contentDescription = "Login con facebook",
                      modifier = Modifier
                          .padding(10.dp)
                          .size(40.dp)
                  )

                  Text(
                      text = "Login con Facebook",
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Bold
                  )
              }*/
        }
    }

}

@Composable
fun UserForm(
    isCreateAccount: Boolean,
    onDone: (String, String) -> Unit = {email, pwd ->}
){
    val email = rememberSaveable {
        mutableStateOf("")
    }

    val password = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }

    val valido = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    //controla que al hacer click en el boton submit, el teclado se oculta
    val keyboardController = LocalSoftwareKeyboardController.current

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            emailState = email
        )

        PasswordInput(
            passwordState = password,
            passwordVisible= passwordVisible
        )

        SubmitButton(
            textId = if(isCreateAccount) "Crear cuenta" else "Login",
            inputValido = valido
            ) {
                onDone(email.value.trim(), password.value.trim())
            //se oculta el teclado
                keyboardController?.hide()
        }

    }
}


@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic: () -> Unit) {

    Button(
        onClick = onClic,
        modifier =Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = CircleShape,
        enabled = inputValido,
    ){
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp)
        )
    }
}


@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valuestate = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email,
        textColor = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valuestate : MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true,
    textColor: Color = Color.White
){
    OutlinedTextField(
        value = valuestate.value,
        onValueChange = { valuestate.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier.padding(
            bottom = 10.dp,
            start = 10.dp,
            end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = TextStyle(color = textColor) // Cambia el color del texto aquí
    )

}



@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    passwordVisible: MutableState<Boolean>,
    labelId: String = "Password",
    ) {
    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()){
                PasswordVisibleIcon(passwordVisible)
            } else null
        }

    )

}



@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image = if (passwordVisible.value)
        Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility

    IconButton(onClick = { passwordVisible.value = !passwordVisible.value } ) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }
}


