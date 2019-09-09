package br.com.newm.teste.ui.customer

interface CustomerContract {

    interface ListView{
        fun setAdapter()
    }

    interface RegisterView{
        fun validateFields() : Boolean
        fun isNameValid() : Boolean
        fun isCpfValid() : Boolean
    }

}