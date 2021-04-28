package com.example.myapplication2


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.databinding.ActivityMainBinding
import com.facebook.share.widget.ShareDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shareAll()
    }

    private fun shareAll(){


        val bottomSheet = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)

        bottomSheet.setContentView(view)


        val btnShare = findViewById<Button>(R.id.btn_share_all)
        btnShare?.setOnClickListener {

            bottomSheet.show()
        }

    }

    // TODO: onClicks

    fun onClickWhatsApp(view: View) {

        val nomeProduto = binding.editNomeProduto.text.toString()
        val precoProduto = binding.editProductPrice.text.toString()
        val nomeMembro = binding.editMemberName.text.toString()
        val linkAppMatchfood = Uri.parse("https://play.google.com/store/apps/details?id=com.matchfood.app")
        val linkSiteMatchfood = Uri.parse("https://matchfood.com/baixe_agora")

        val text: String = "*$nomeProduto* "+"por apenas"+" *R$$precoProduto?* \n\n" +
                "Você só encontra na nossa loja *$nomeMembro* "+"no APP"+" *Matchfood* " +
                "\uD83D\uDE03 \n"+ //emoji
                "$linkAppMatchfood \n\n" +
                "Aceitamos várias formas de pagamento! \uD83D\uDCB3 \n\n" + //emoji
                "Baixe o app Matchfood e faça seu pedido: \n$linkSiteMatchfood"
        if (!text.isEmpty()) {
            startIcoShareTextWhatsapp(text)
        } else {
            Toast.makeText(applicationContext, "Texto esta vazio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startIcoShareTextWhatsapp(text: String?) {
        val sendIntent = Intent()
        try{
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"
            //startActivity(sendIntent)

            sendIntent.setPackage("com.whatsapp")

            startActivity(sendIntent)
            // startActivity(Intent.createChooser(sendIntent, "Share to"))
        }catch (e: Exception){
            Toast.makeText(applicationContext, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
        }

    }

    fun onClickFacebook(view: View) {

        val nomeProduto = binding.editNomeProduto.text.toString()
        val precoProduto = binding.editProductPrice.text.toString()
        val nomeMembro = binding.editMemberName.text.toString()
        val linkAppMatchfood = Uri.parse("www.minhalojaMFM.com")
        val linkSiteMatchfood = Uri.parse("https://matchfood.com/baixe_agora")

        val message = "$nomeProduto por apenas R$$precoProduto? Você só encontra" +
                " na nossa loja virtual $nomeMembro no $linkAppMatchfood \uD83D\uDE03" + //@appmatchfood -> Link para o Play Store
                "\nAceitamos várias formas de pagamento! \uD83D\uDCB3" +
                "\nBaixe o app Matchfood e faça seu pedido no $linkSiteMatchfood"


        val hasTag = ShareHashtag.Builder().setHashtag("#soumatchfood #incentiveocomerciolocal #matchfood").build()
        val imageBitmap = BitmapFactory.decodeResource(resources, R.drawable.image)


        // Envia a imagem
        val photo = SharePhoto.Builder()
                .setCaption(message) //Termos do facebook -> Sets the user generated caption for the photo.
                // Note that the 'caption' must come from the user, as pre-filled content is forbidden by the Platform Policies (2.3)
                .setBitmap(imageBitmap)
                .build()
        val shareContentPhoto = SharePhotoContent.Builder()
                .addPhoto(photo)
                .setShareHashtag(hasTag)
                .build()

        // Como o link já pode compartilhar com text
        val shareContentLink = ShareLinkContent.Builder()
                .setQuote(message)
                .setShareHashtag(hasTag)
                .setContentUrl(Uri.parse("android.resource://com.example.sharefacebook/" + R.drawable.image2)) //
                .build()


        ShareDialog.show(this@MainActivity, shareContentPhoto)

    }

    fun onClickInstagram(view: View) {

        //com.example.sharefacebook/ -> No AndroidManifest
        val shareImage = Uri.parse("android.resource://com.example.sharefacebook/" + R.drawable.image2)

        createIcoInstagramIntent(shareImage)
    }

    private fun createIcoInstagramIntent(uri: Uri?){

        val text: String = "API Testing"

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "*/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "API Testing")// Não funciona

        shareIntent.setPackage("com.instagram.android")
        startActivity(shareIntent) //-- Antigo
        //startActivity(Intent.createChooser(shareIntent, "Share to")) // Mais opções de compartilhamento
    }

    fun onClickTelegram(view: View) {

        val nomeProduto = binding.editNomeProduto.text.toString()
        val precoProduto = binding.editProductPrice.text.toString()
        val nomeMembro = binding.editMemberName.text.toString()
        val linkAppMatchfood = Uri.parse("https://play.google.com/store/apps/details?id=com.matchfood.app")
        val linkSiteMatchfood = Uri.parse("https://matchfood.com/baixe_agora")

        val text: String = "*$nomeProduto* "+"por apenas"+" *R$$precoProduto?* \n\n" +
                "Você só encontra na nossa loja *$nomeMembro* "+"no APP"+" *Matchfood* " +
                "\uD83D\uDE03 \n"+ //emoji
                "$linkAppMatchfood \n\n" +
                "Aceitamos várias formas de pagamento! \uD83D\uDCB3 \n\n" + //emoji
                "Baixe o app Matchfood e faça seu pedido: \n$linkSiteMatchfood"
        if (!text.isEmpty()) {
            startIcoShareTextTelegram(text)
        } else {
            Toast.makeText(applicationContext, "Texto esta vazio", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startIcoShareTextTelegram(text: String?) {
        val sendIntent = Intent()
        try {
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"

            sendIntent.setPackage("org.telegram.messenger")

            startActivity(sendIntent)
        }catch (e: Exception){
            Toast.makeText(applicationContext, "Telegram not Installed", Toast.LENGTH_SHORT).show()
        }

    }

    fun onClickTwitter(view: View) {

        val nomeProduto = binding.editNomeProduto.text.toString()
        val precoProduto = binding.editProductPrice.text.toString()
        val nomeMembro = binding.editMemberName.text.toString()
        val linkAppMatchfood = Uri.parse("https://play.google.com/store/apps/details?id=com.matchfood.app")
        val linkSiteMatchfood = Uri.parse("https://matchfood.com/baixe_agora")


        val text: String = "$nomeProduto "+"por apenas"+" R$$precoProduto? \n\n" +
                "Você só encontra na nossa loja $nomeMembro "+"no APP"+" Matchfood " +
                "\uD83D\uDE03 \n"+ //emoji
                "$linkAppMatchfood \n\n" +
                "Aceitamos várias formas de pagamento! \uD83D\uDCB3 \n\n" + //emoji
                "Baixe o app Matchfood e faça seu pedido: \n$linkSiteMatchfood"



        //com.example.sharefacebook/ -> No AndroidManifest
        val shareImage = Uri.parse("android.resource://com.example.sharefacebook/" + R.drawable.image2)

        createIcoTwitterIntent(shareImage, text)

    }

    private fun createIcoTwitterIntent(uri: Uri?, text: String?){


        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "*/*" // Funciona no twitter
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        shareIntent.setPackage("com.twitter.android")
        startActivity(shareIntent) //-- Antigo
        //startActivity(Intent.createChooser(shareIntent, "Share to")) // Mais opções de compartilhamento
    }

    fun onClickLinkeIn(view: View) {

        val nomeProduto = binding.editNomeProduto.text.toString()
        val precoProduto = binding.editProductPrice.text.toString()
        val nomeMembro = binding.editMemberName.text.toString()
        val linkAppMatchfood = Uri.parse("https://play.google.com/store/apps/details?id=com.matchfood.app")
        val linkSiteMatchfood = Uri.parse("https://matchfood.com/baixe_agora")


        val text: String = "$nomeProduto "+"por apenas"+" R$$precoProduto? \n\n" +
                "Você só encontra na nossa loja $nomeMembro "+"no APP"+" Matchfood " +
                "\uD83D\uDE03 \n"+ //emoji
                "$linkAppMatchfood \n\n" +
                "Aceitamos várias formas de pagamento! \uD83D\uDCB3 \n\n" + //emoji
                "Baixe o app Matchfood e faça seu pedido: \n$linkSiteMatchfood"

        //com.example.sharefacebook/ -> No AndroidManifest
        val shareImage = Uri.parse("android.resource://com.example.sharefacebook/" + R.drawable.image2)

        createIcoLinkedinIntent(shareImage, text)

    }

    private fun createIcoLinkedinIntent(uri: Uri?, text: String?){


        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "*/*" // Funciona no Linkedin
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        shareIntent.setPackage("com.linkedin.android")
        //shareIntent.setPackage("com.google.android.gm")
        startActivity(shareIntent) //-- Antigo
        //startActivity(Intent.createChooser(shareIntent, "Share to")) // Mais opções de compartilhamento
    }

    fun onClickEmail(view: View) {

        val nomeProduto = binding.editNomeProduto.text.toString()
        val precoProduto = binding.editProductPrice.text.toString()
        val nomeMembro = binding.editMemberName.text.toString()
        val linkAppMatchfood = Uri.parse("https://play.google.com/store/apps/details?id=com.matchfood.app")
        val linkSiteMatchfood = Uri.parse("https://matchfood.com/baixe_agora")


        val text: String = "$nomeProduto "+"por apenas"+" R$$precoProduto? \n\n" +
                "Você só encontra na nossa loja $nomeMembro "+"no APP"+" Matchfood " +
                "\uD83D\uDE03 \n"+ //emoji
                "$linkAppMatchfood \n\n" +
                "Aceitamos várias formas de pagamento! \uD83D\uDCB3 \n\n" + //emoji
                "Baixe o app Matchfood e faça seu pedido: \n$linkSiteMatchfood"

        //com.example.sharefacebook/ -> No AndroidManifest
        //val shareImage = Uri.parse("android.resource://com.example.sharefacebook/"+R.drawable.image2)


        createIcoEmailIntent(text)

    }

    private fun createIcoEmailIntent(text: String?){

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/*"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Teste de compartilhamento") // Assunto do email
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


        shareIntent.setPackage("com.google.android.gm")

        startActivity(shareIntent) //-- Antigo
        //startActivity(Intent.createChooser(shareIntent, "Share to")) // Mais opções de compartilhamento
    }

}