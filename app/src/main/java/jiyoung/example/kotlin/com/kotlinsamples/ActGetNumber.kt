package jiyoung.example.kotlin.com.kotlinsamples

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import kotlinx.android.synthetic.main.act_getnumber.*
import android.provider.ContactsContract
import android.content.ContentProviderOperation
import android.widget.Toast






class ActGetNumber : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_getnumber)

        btn_getnumber.setOnClickListener {
            val telManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var phoneNum = telManager.line1Number
            if (phoneNum.startsWith("+82")) {
                tv_number.text = phoneNum.replace("+82", "0")
            } else if (phoneNum.isEmpty()) {
                tv_number.text = "번호없음"
            } else {
                tv_number.text = "내 번호는 "+ phoneNum
            }
        }

        btn_save.setOnClickListener { saveNumber() }


    }

    private fun saveNumber() {
        val DisplayName = et_name.text.toString()
        val MobileNumber = et_number.text.toString()

        val ops = ArrayList<ContentProviderOperation>()

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build())

        //------------------------------------------------------ Names
        if (!DisplayName.isEmpty()) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build())
        }

        //------------------------------------------------------ Mobile Number
        if (!MobileNumber.isEmpty()) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build())
        }

        // Asking the Contact provider to create a new contact
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Exception: " + e.message, Toast.LENGTH_SHORT).show()
        }

    }
}
