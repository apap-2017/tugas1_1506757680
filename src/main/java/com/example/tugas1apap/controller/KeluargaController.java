package com.example.tugas1apap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.tugas1apap.service.KeluargaService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;
	
	 @RequestMapping("/keluarga")
	    public String selectKeluarga (Model model,
	            @RequestParam(value = "nkk", required = false) String nkk)
	    {
	     log.info(nkk + " nkk");   
	     
		 KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
	        
	        if (keluarga != null) {
	            model.addAttribute ("keluarga", keluarga);
	            return "datakeluarga";
	        } else {
	            model.addAttribute ("nkk", nkk);
	            return "not-found";
	        }
	    }
	 
	 @RequestMapping("/keluarga/tambah")
	    public String addKeluarga (Model model)
	    {
		 List<KelurahanModel> listKelurahan = keluargaDAO.getAllKelurahan();
		 
		 model.addAttribute("listKelurahan", listKelurahan);
		 model.addAttribute("keluarga", new KeluargaModel());
	        return "addKeluarga";
	    }
	 
	 @RequestMapping(value = "/keluarga/tambah/submit", method = RequestMethod.POST)
	    public String addKeluargaSubmit (Model model,
	    		@RequestParam(value = "alamat", required = true)String alamat,
	    		@RequestParam(value="rt", required = true) String rt,
	    		@RequestParam(value="rw", required = true) String rw,
	    		@RequestParam(value="id_kelurahan", required = true) String id_kelurahan,
	    		@RequestParam(value="kecamatan", required = true) String kecamatan,
	    		@RequestParam(value="kota", required = true) String kota)
	    {
		 KeluargaModel keluargaBaru = new KeluargaModel();
		 keluargaBaru.setAlamat(alamat);
		 keluargaBaru.setRt(rt);
		 keluargaBaru.setRw(rw);
		 keluargaBaru.setId_kelurahan(id_kelurahan);
		 
		 //generate nkk
		 keluargaDAO.addKeluarga(keluargaBaru);
		 model.addAttribute("keluarga", keluargaBaru);
		 return "success-add-family";
	    }
	 
	 @RequestMapping("/keluarga/ubah/{nkk}")
	    public String update(Model model, @RequestParam(value = "nkk") String nkk)
	    {
	    	KeluargaModel keluarga = keluargaDAO.selectKeluarga (nkk);
	    		if(keluarga != null) {
	    			log.info(nkk + " update nkk");

	    			 List<KelurahanModel> listKelurahan = keluargaDAO.getAllKelurahan();
	    			 
	    			model.addAttribute("listKelurahan", listKelurahan);
	    			model.addAttribute("nomor_kk", nkk);
	    			model.addAttribute("keluarga", keluarga);

	    			return "updateKeluarga";
	    		} 
	    		else {
	    			model.addAttribute("nkk", nkk);
	    			return "not-found";
	    		}
	    }
	 
	 @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
		public String updateKeluargaSuccess(@ModelAttribute KeluargaModel keluarga, Model model) {
			
	    	KeluargaModel family = keluarga;
	        keluargaDAO.updateKeluarga(family);
	        model.addAttribute("keluarga", keluarga);
			return "success-update-keluarga";
		}
}
