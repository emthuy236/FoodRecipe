package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodappnew.databinding.RecipesRowLayoutBinding
import com.example.foody.models.FoodRecipe
import com.example.foody.models.Result
import ultil.RecipeDiffutil

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.MyViewHolder> () {
    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :RecyclerView.ViewHolder(binding.root) {



        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipes = recipes[position]
        holder.bind(currentRecipes)
    }
    fun setdata(newdata: FoodRecipe){
        val recipeDiffUil = RecipeDiffutil(recipes,newdata.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUil)
        recipes = newdata.results
        diffUtilResult.dispatchUpdatesTo(this)

    }
}