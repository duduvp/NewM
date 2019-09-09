package br.com.newm.teste.ui.main

import android.widget.Toast
import br.com.newm.teste.data.local.AppDatabase
import br.com.newm.teste.data.local.dao.CustomerDao
import br.com.newm.teste.data.local.entity.Customer
import br.com.newm.teste.ui.customer.ListCustomerFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainPresenter(mainView: MainActivity) : MainContract.Presenter, CoroutineScope {

    private var view: MainActivity = mainView
    private val db: AppDatabase
    private val customerDao: CustomerDao
    private val job = Job()

    init {
        view.initViews()
        db = AppDatabase.getDatabase(view)
        customerDao = db.customerDao()
    }

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override fun saveCustomer(customer: Customer) {
        launch {
            try {
                customerDao.insert(customer)
                withContext(Dispatchers.Main) {
                    Toast.makeText(view, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    view.replaceFragment(ListCustomerFragment(view.presenter))
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        view,
                        String.format("Erro ao registrar o cliente! %s", e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun loadCustomers(): List<Customer> {
        var list: List<Customer> = ArrayList()
        launch {
            list = customerDao.queryAll()
        }
        return list
    }

    override fun loadCustomersFiltered(filter: String): List<Customer> {
        var list: List<Customer> = ArrayList()
        launch {
            list = customerDao.findByNameOrEmail(filter)
        }
        return list
    }

    override fun deleteCustomer(customer: Customer?) {
        launch {
            try {
                customerDao.delete(customer)
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        view,
                        String.format("Erro ao apagar o cliente! %s", e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

}