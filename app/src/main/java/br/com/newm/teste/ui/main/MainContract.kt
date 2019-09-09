package br.com.newm.teste.ui.main

import androidx.fragment.app.Fragment
import br.com.newm.teste.data.local.entity.Customer

interface MainContract {
    interface View {
        fun initViews()
        fun replaceFragment(fragment: Fragment)

    }

    interface Presenter {
        fun saveCustomer(customer: Customer)
        fun loadCustomers(): List<Customer>
        fun loadCustomersFiltered(filter: String): List<Customer>
        fun deleteCustomer(customer: Customer?)
    }
}