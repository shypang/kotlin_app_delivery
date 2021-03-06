package com.test_esy.esyz2.Fragment.MarketInfo


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.test_esy.esyz2.R
import kotlinx.android.synthetic.main.fragment_review.view.*

/**
 * A simple [Fragment] subclass.
 */
class ReviewFragment : Fragment() {

    //데이터를 어댑터에 넣어주는 작업

    private val db = FirebaseFirestore.getInstance()

    private val text_array = ArrayList<String>()
    private val nickname_array = ArrayList<String>()
    private val rating_array = ArrayList<String>()


    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_review, container, false)


        auth = FirebaseAuth.getInstance() //초기화하는 부분

        val view : View = inflater.inflate(R.layout.fragment_review, container, false)


        //어댑터 연결
        val review_adapter = ReviewListAdapter(requireContext(),nickname_array, text_array , rating_array )
        view.listview_review.adapter = review_adapter


        db.collection("reviews")//firebase에서 데이터 받아옴
            .get()
            .addOnSuccessListener { result ->

                for(document in result){
                    rating_array.add(document.get("rating") as String)
                    text_array.add(document.get("text") as String)
                    nickname_array.add(document.get("writer") as String)
                }
                review_adapter.notifyDataSetChanged() //어댑터에 넣어줌

            }
            .addOnFailureListener{exception ->


            }

        view.write_button.setOnClickListener{

            if(auth.currentUser == null){
                Toast.makeText(requireContext(), "회원가입 or 로그인 해주세요", Toast.LENGTH_LONG).show()
            }
            else{

                val intent = Intent(requireContext(), WriteActivity::class.java)
                startActivity(intent)


            }

            val intent  = Intent(requireContext(), WriteActivity::class.java)
            startActivity(intent)
        }
        return view

    }


}
