package br.com.newm.teste.ui.customer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import br.com.newm.teste.R
import br.com.newm.teste.data.local.entity.Customer
import br.com.newm.teste.ui.main.MainPresenter
import kotlinx.android.synthetic.main.fragment_list_customer.*
import kotlinx.android.synthetic.main.fragment_register_customer.*

class ListCustomerFragment(mainPresenter: MainPresenter?) : Fragment(), CustomerContract.ListView {

    private val presenter = mainPresenter
    private var customerList: List<Customer>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerCustomer)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )
        imgSearch.setOnClickListener {
            customerList = if (edtSearch.text.toString().isNullOrEmpty())
                presenter?.loadCustomers()
            else
                presenter?.loadCustomersFiltered(edtSearch.text.toString())
            setAdapter()
        }
    }

    override fun setAdapter() {
        recyclerCustomer.adapter = CustomerAdapter(customerList, object : CustomerListener {
            override fun onClickListener(customer: Customer?) { //TODO UPDATE
            }

            override fun onLongClickListener(customer: Customer?) {
                presenter?.deleteCustomer(customer)
                customerList?.toMutableList()?.remove(customer)
                setAdapter()
            }

        })
    }
}
