package br.com.newm.teste.ui.customer

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.newm.teste.R
import br.com.newm.teste.data.local.entity.Customer

class CustomerAdapter(
    var customerList: List<Customer>?,
    val listener: CustomerListener
) :
    RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(customerList?.get(position), listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_customer, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (customerList == null)
            0
        else
            customerList?.size!!
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.txtName)
        var email: TextView = itemView.findViewById(R.id.txtEmail)
        var cpf: TextView = itemView.findViewById(R.id.txtCpf)
        var celular: TextView = itemView.findViewById(R.id.txtPhone)
        var nascimento: TextView = itemView.findViewById(R.id.txtDate)
        var address: TextView = itemView.findViewById(R.id.txtAddress)
        fun bindView(customer: Customer?, listener: CustomerListener) {
            name.text = customer?.name
            email.text = customer?.email
            cpf.text = customer?.cpf
            celular.text = customer?.phone
            nascimento.text = customer?.birthDate
            address.text = customer?.address
            itemView.setOnLongClickListener {
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Atenção")
                alertDialogBuilder.setMessage("Deseja remover esse cliente?")
                alertDialogBuilder.setPositiveButton("Sim!") { _, _ ->
                    listener.onLongClickListener(customer)
                }
                alertDialogBuilder.setNegativeButton("Não", null)
                alertDialogBuilder.show()
                true
            }
        }
    }
}