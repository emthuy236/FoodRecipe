import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foody.models.FoodRecipe
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import data.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import ultil.NetworkResult
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    val repository: Repository, application: Application) : AndroidViewModel(application) {
    val recipeRespone: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getrecipe(queries: Map<String, String>) = viewModelScope.launch {
        getrecipeSafeCall(queries)
    }

   // @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun getrecipeSafeCall(queries: Map<String, String>) {
       recipeRespone.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val respone = repository.remote.getRecipes(queries)
                recipeRespone.value = handleFoodRecipeRespone(respone)
            } catch (e: Exception) {
                recipeRespone.value = NetworkResult.Error("Recipes not Found")

            }
        } else {
            recipeRespone.value = NetworkResult.Error("No Internet Connection!")
        }

    }

    private fun handleFoodRecipeRespone(respone: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            respone.message().toString().contains("timeout") -> {
                return NetworkResult.Error("time out")
            }
            respone.code() == 402 -> {
                return NetworkResult.Error("API KEY limited.")
            }
            respone.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipe not found")
            }
            respone.isSuccessful -> {
                val foodRecipes = respone.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(respone.message())
            }
        }
    }

    //@RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capability = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }
}