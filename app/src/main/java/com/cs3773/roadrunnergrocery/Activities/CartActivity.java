package com.cs3773.roadrunnergrocery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cs3773.roadrunnergrocery.Adapters.CartAdapter;
import com.cs3773.roadrunnergrocery.Models.Cart;
import com.cs3773.roadrunnergrocery.Models.ProductBase;
import com.cs3773.roadrunnergrocery.Models.ProductPair;
import com.cs3773.roadrunnergrocery.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    List<ProductPair> mProducts;
    ProductBase mProductBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mProductBase = new ProductBase();
        setupRecyclerView();
        setupCheckoutButton();
        setupTotalPrice();
    }

    private void setupCheckoutButton() {
        findViewById(R.id.checkout_layout).setOnClickListener(v -> {
            if (mProducts.size() > 0) {
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.cart_recycler);

        Cart cart = Cart.getInstance();
        mProducts = cart.getItems();

        CartAdapter cartAdapter = new CartAdapter(mProducts, this);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupTotalPrice() {
        Cart cart = Cart.getInstance();
        double total = 0;
        for (ProductPair pp : cart.getItems()) {
            total += pp.getProduct().getPrice() * pp.getQuantity();
        }
        TextView totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(String.format("Cart Total: $%.2f", total));
    }
}
