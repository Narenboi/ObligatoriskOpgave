import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.obligatoriskopgave.models.Beer
import com.example.obligatoriskopgave.repositories.BeerService
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeerRepository {

    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"
    private val beerService: BeerService
    val beersLiveData: MutableLiveData<List<Beer>> = MutableLiveData<List<Beer>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val reloadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val deleteBeerErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val beerLiveData: MutableLiveData<List<Beer>> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerService = build.create(BeerService::class.java)
        getBeers()
    }

    fun getBeers() {
        val user = FirebaseAuth.getInstance().currentUser
        val userEmail = user?.email

        userEmail?.let {
            reloadingLiveData.value = true
            beerService.getAllBeers(userEmail).enqueue(object : Callback<List<Beer>> {
                override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                    reloadingLiveData.value = false
                    if (response.isSuccessful) {
                        val beers: List<Beer>? = response.body()
                        beersLiveData.postValue(beers ?: emptyList())
                        errorMessageLiveData.postValue("")
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        errorMessageLiveData.postValue(message)
                        Log.d("BeerRepository", message)
                    }
                }

                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    errorMessageLiveData.postValue(t.message)
                    Log.d("BeerRepository", t.message!!)
                }
            })
        } ?: run {
            errorMessageLiveData.postValue("User not logged in")
            Log.d("BeerRepository", "User not logged in")
        }
    }


    fun addBeer(beer: Beer) {
        val user = FirebaseAuth.getInstance().currentUser
        val userEmail = user?.email

        userEmail?.let {
            reloadingLiveData.value = true
            beerService.saveBeer(beer).enqueue(object : Callback<List<Beer>> {
                override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                    reloadingLiveData.value = false
                    if (response.isSuccessful) {
                        val beers: List<Beer>? = response.body()
                        beersLiveData.postValue(beers ?: emptyList())
                        updateMessageLiveData.postValue("Beer added successfully")
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        errorMessageLiveData.postValue(message)
                        Log.d("BeerRepository", message)
                    }
                }

                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    errorMessageLiveData.postValue(t.message)
                    Log.d("BeerRepository", t.message!!)
                }
            })
        } ?: run {
            errorMessageLiveData.postValue("User not logged in")
            Log.d("BeerRepository", "User not logged in")
        }
    }

    fun updateBeer(id: Int, updatedBeer: Beer) {
        val user = FirebaseAuth.getInstance().currentUser
        val userEmail = user?.email

        userEmail?.let {
            reloadingLiveData.value = true
            beerService.updateBeer(id, updatedBeer).enqueue(object : Callback<List<Beer>> {
                override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                    reloadingLiveData.value = false
                    if (response.isSuccessful) {
                        val beers: List<Beer>? = response.body()
                        beersLiveData.postValue(beers ?: emptyList())
                        updateMessageLiveData.postValue("Beer updated successfully")
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        errorMessageLiveData.postValue(message)
                        Log.d("BeerRepository", message)
                    }
                }

                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    errorMessageLiveData.postValue(t.message)
                    Log.d("BeerRepository", t.message!!)
                }
            })
        } ?: run {
            errorMessageLiveData.postValue("User not logged in")
            Log.d("BeerRepository", "User not logged in")
        }
    }

    fun deleteBeer(id: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        val userEmail = user?.email

        userEmail?.let {
            reloadingLiveData.value = true
            beerService.deleteBeer(id).enqueue(object : Callback<List<Beer>> {
                override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                    reloadingLiveData.value = false
                    if (response.isSuccessful) {
                        val beers: List<Beer>? = response.body()
                        beersLiveData.postValue(beers ?: emptyList())
                        updateMessageLiveData.postValue("Beer deleted successfully")
                    } else {
                        val message = response.code().toString() + " " + response.message()
                        errorMessageLiveData.postValue(message)
                        Log.d("BeerRepository", message)
                    }
                }

                override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                    errorMessageLiveData.postValue(t.message)
                    Log.d("BeerRepository", t.message!!)
                }
            })
        } ?: run {
            errorMessageLiveData.postValue("User not logged in")
            Log.d("BeerRepository", "User not logged in")
        }
    }

    fun updateBeersLiveData(beers: List<Beer>) {
        beersLiveData.postValue(beers)
    }
}
