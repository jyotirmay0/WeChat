package com.example.wechat

import android.provider.ContactsContract.CommonDataKinds.Email

class User {

    var name :String?=null
    var email :String?=null
    var uid :String?=null

    constructor(){}
    constructor(name:String?,email: String?,uid:String?)
    {
        this.email=email
        this.name=name
        this.uid=uid

    }


}