package bindingadapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
//import coil.load
import com.example.foodappnew.R
import com.squareup.picasso.Picasso

class RecipesRowBinding {

    companion object{
        @BindingAdapter("loadImageFromUrl")
       // @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl:String){
            imageView.load(imageUrl){
                crossfade(600)
            }
        }
        @BindingAdapter("setNumberOfLikes")
      //  @JvmStatic
        fun setNumberOfLikes(textView: TextView, like:Int){
            textView.text = like.toString()
        }
       // @JvmStatic
        @BindingAdapter("setNumberOfMinute")
        fun setNumberOfMinute(textView: TextView, minutes:Int){
            textView.text = minutes.toString()
        }
        @BindingAdapter("applyVeganColer")
      //  @JvmStatic
        fun applyVeganColer(view: View, vegan:Boolean){
            if (vegan){
                when(view) {
                    is TextView ->{
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView ->{
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }
    }
}