package ir.dimyadi.mywidget

import android.os.Bundle
import android.text.util.Linkify
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author MEHDIMYADI
 **/

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)

        //Version of app
        val versionName = findViewById<TextView>(R.id.version)
        versionName.text = BuildConfig.VERSION_NAME

        //My Website
        val website = findViewById<TextView>(R.id.my_website)
        website.setText(R.string.my_web_site)
        Linkify.addLinks(website, Linkify.WEB_URLS)

        //My Github
        val myGithub = findViewById<TextView>(R.id.my_github)
        myGithub.setText(R.string.github)
        Linkify.addLinks(myGithub, Linkify.WEB_URLS)
    }
}