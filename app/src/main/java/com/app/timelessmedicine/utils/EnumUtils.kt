
package com.app.timelessmedicine.utils


object EnumUtils {
    val TAG = EnumUtils::class.simpleName

    enum class DeviceType(val value: String) {
        ANDROID("1"), IOS("2")

    }

    enum class GenderType(val value: String) {
        MALE("m"), FEMALE("f"), OTHER("o")

    }

    enum class LoginType(val value: String) {
        MANUAL("1"), FACEBOOK("2"), GOOGLE("2")

    }

    enum class UserType(val value:String){
        USER("0"), SERVICE_PROVIDER("1")
    }
    enum class SortType(val value:String){
        RELEVANCE("0"), DATE_POSTED("1"), PRICE_LOWEST("2"),PRICE_HIGHEST("3")
    }
    enum class ViewType(val value:String){
        GALLERY("0"), LIST("1"), MOSIAC("2")
    }

    enum class MyOrderType(val value: String) {
        RECEIVE_ORDER("1"), MY_ORDER("2")

    }

    enum class MyPayment(val value: String) {
        INCOME("1"), PAYING("2")

    }
    enum class OrderType(val value: String) {
        GIVEN("1"), TAKEN("2")

    }
    enum class MyOrderStatus(val value: String) {
        ONGOING("1"), PAST("2")

    }

    enum class PaymentType(val value: String) {
        CARD("1"), COD("2")

    }

    enum class MONTHS{Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec}

    enum class FileType {
        TEXT, IMAGE,FILE
    }

    enum class AttachementTypeKey(val value: String) {
        IMAGE("1"),
        FILE("2")
    }
    enum class SendAttachmentKey(val value: String) {
        IMAGE("1"),
        FILE("2")


    }



}