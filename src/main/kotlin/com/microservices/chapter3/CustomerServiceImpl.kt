package com.microservices.chapter3

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService{

    companion object {
        val initialCustomers = arrayOf(Customer(1, "Kotlin", Telephone("+44", "2345342312")),
                Customer(2, "Spring", Telephone("+33", "12345678")),
                Customer(3, "Microservices", Telephone("+45", "453215465")))
    }

    private val customers = ConcurrentHashMap<Int, Customer>(initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Int) = customers[id]

    override fun createCustomer(customer: Customer) {
        customers[customer.id] = customer
    }

    override fun deleteCustomer(id: Int) {
        customers.remove(id)
    }

    override fun updateCustomer(id: Int, customer: Customer) {
        deleteCustomer(id)
        createCustomer(customer)
    }

    override fun searchCustomer(nameFilter: String): List<Customer> {
        return customers
                .filter { it.value.name.contains(nameFilter, true) }
                .map(Map.Entry<Int, Customer>::value).toList()
    }

}