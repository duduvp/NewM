package br.com.newm.teste.ui.customer

import br.com.newm.teste.data.local.entity.Customer

interface CustomerListener {
    fun onClickListener(customer : Customer?) //TODO UPDATE
    fun onLongClickListener(customer : Customer?)
}