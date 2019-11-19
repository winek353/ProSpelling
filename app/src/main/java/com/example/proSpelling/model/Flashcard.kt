package com.example.proSpelling.model

class Flashcard{
    var id : Int = 0
    var obverse : String = ""
    var reverse : String = ""

    constructor(obverse:String, reverse:String){
        this.obverse = obverse
        this.reverse = reverse
    }
}