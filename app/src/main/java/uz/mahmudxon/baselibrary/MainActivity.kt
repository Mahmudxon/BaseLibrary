package uz.mahmudxon.baselibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.mahmudxon.library.ui.BaseFragment
import uz.mahmudxon.library.util.startFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
