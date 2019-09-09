package br.com.newm.teste.ui.customer


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.newm.teste.R
import br.com.newm.teste.data.local.entity.Customer
import br.com.newm.teste.ui.main.MainPresenter
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_register_customer.*
import java.util.regex.Pattern


class RegisterCustomerFragment(mainPresenter: MainPresenter?) : Fragment(),
    CustomerContract.RegisterView {

    private val presenter = mainPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSave.setOnClickListener {
            if (validateFields()) {
                presenter?.saveCustomer(
                    Customer(
                        0,
                        edtName.text.toString(),
                        edtDate.text.toString(),
                        edtCpf.text.toString(),
                        edtPhone.text.toString(),
                        edtMail.text.toString(),
                        edtAddress.text.toString(),
                        edtObs.text.toString()
                    )
                )
            }
        }
        val cpfListener = MaskedTextChangedListener("[000]{.}[000]{.}[000]{-}[00]", edtCpf)
        edtCpf.addTextChangedListener(cpfListener)
        edtCpf.onFocusChangeListener = cpfListener
        val phoneListener = MaskedTextChangedListener("([00]) [00000]-[0000]", edtPhone)
        edtPhone.addTextChangedListener(phoneListener)
        edtPhone.onFocusChangeListener = phoneListener
        val dateListener = MaskedTextChangedListener("[00]{/}[00]{/}[9900]", edtDate)
        edtDate.addTextChangedListener(dateListener)
        edtDate.onFocusChangeListener = dateListener
    }

    override fun validateFields(): Boolean {
        if (edtName.text.toString().isEmpty()) {
            edtName.error = getString(R.string.nome_obrigatorio)
            edtName.requestFocus()
            return false
        }
        if (!isNameValid()) {
            edtName.error = getString(R.string.nome_invalido)
            edtName.requestFocus()
            return false
        }
        edtName.error = null
        if (edtDate.text.toString().isEmpty()) {
            edtDate.error = getString(R.string.data_obrigatorio)
            edtDate.requestFocus()
            return false
        }
        edtDate.error = null
        if (edtCpf.text.toString().isEmpty()) {
            edtCpf.error = getString(R.string.cpf_obrigatorio)
            edtCpf.requestFocus()
            return false
        }
        if (!isCpfValid()) {
            edtCpf.error = getString(R.string.cpf_invalido)
            edtCpf.requestFocus()
            return false
        }
        edtCpf.error = null
        if (edtPhone.text.toString().isEmpty() || edtPhone.text.toString().length < 15) {
            edtPhone.error = getString(R.string.celular_obrigatorio)
            edtPhone.requestFocus()
            return false
        }
        edtPhone.error = null
        if (edtMail.text.toString().isEmpty()) {
            edtMail.error = getString(R.string.email_obrigatorio)
            edtMail.requestFocus()
            return false
        }
        edtMail.error = null
        if (edtAddress.text.toString().isEmpty()) {
            edtAddress.error = getString(R.string.endereco_obrigatorio)
            edtAddress.requestFocus()
            return false
        }
        edtAddress.error = null
        return true
    }

    override fun isNameValid(): Boolean {
        val regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
        if (regex.matcher(edtName.text.toString()).find()) {
            return false
        }
        return true
    }

    override fun isCpfValid(): Boolean {
        if (TextUtils.isEmpty(edtCpf.text.toString())) return false
        val numbers = arrayListOf<Int>()
        edtCpf.text?.filter { it.isDigit() }?.forEach {
            numbers.add(it.toString().toInt())
        }
        if (numbers.size != 11) return false
        (0..9).forEach { n ->
            val digits = arrayListOf<Int>()
            (0..10).forEach { digits.add(n) }
            if (numbers == digits) return false
        }
        val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }
        val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }
        return numbers[9] == dv1 && numbers[10] == dv2
    }

}
