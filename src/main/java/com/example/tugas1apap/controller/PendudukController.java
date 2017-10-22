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
import com.example.model.PendudukModel;
import com.example.tugas1apap.service.KeluargaService;
import com.example.tugas1apap.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;
	KeluargaService keluargaDAO;
	
	 @RequestMapping("/")
	    public String form ()
	    {
	        return "searchform";
	    }
	 @RequestMapping("/penduduk")
	    public String selectPenduduk (Model model,
	            @RequestParam(value = "nik", required = false) String nik)
	    {
	     log.info(nik + " nik");   
		 PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
	        
	        if (penduduk != null) {
	            model.addAttribute ("penduduk", penduduk);
	            return "datapenduduk";
	        } else {
	            model.addAttribute ("nik", nik);
	            return "not-found";
	        }
	    }
	 
	 @RequestMapping("/penduduk/tambah")
	    public String addPenduduk (Model model)
	    {
		 model.addAttribute("penduduk", new PendudukModel());
	        return "addPenduduk";
	    }
	 
	 @RequestMapping(value = "/penduduk/tambah/submit", method = RequestMethod.POST)
	    public String addPendudukSubmit (@ModelAttribute PendudukModel penduduk, Model model)
	    {
		 PendudukModel pendudukBaru = pendudukDAO.addPenduduk(penduduk);
		 model.addAttribute("penduduk", pendudukBaru);
		 return "success-add-citizen";
	    }
	 
	 @RequestMapping("/penduduk/update")
	    public String updatePenduduk ()
	    {
	        return "search-update-form";
	    }
	 
	 @RequestMapping("/penduduk/ubah/{nik}")
	    public String update(Model model, @RequestParam(value = "nik") String nik)
	    {
	    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
	    		if(penduduk != null) {
	    			log.info(nik + " update nik");   
	    			model.addAttribute("penduduk", penduduk);

	    			return "updatePenduduk";
	    		} 
	    		else {
	    			model.addAttribute("nik", nik);
	    			return "not-found";
	    		}
	    }
	 
	 @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
		public String updatePendudukSuccess(@ModelAttribute PendudukModel penduduk, Model model) {
			
	    	PendudukModel citizen = penduduk;
	        pendudukDAO.updatePenduduk(citizen);
	        model.addAttribute("penduduk", penduduk);
			return "success-update";
		}
	 
	 @RequestMapping(value = "/penduduk/mati")
		public String pendudukIsWafat(Model model,
	            @RequestParam(value = "nik", required = false) String nik) {
		 	log.info(nik + " mati");
		 	pendudukDAO.pendudukIsWafat(nik);
	    	model.addAttribute("nik", nik);
	    	
	    	//cek status kematian anggota keluarga
	    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
	    	String id_kel = penduduk.getId_keluarga();
	    	log.info("keluargnya " + id_kel);
	    	
	    	List<PendudukModel> family_member = pendudukDAO.selectFamilyMember(id_kel);
	    	KeluargaModel keluarga = pendudukDAO.getFamily(id_kel);
	    	
	    	boolean semuaMati = true;
	    	
		    	for(int i = 0; i < family_member.size(); i++) {
		    		if(family_member.get(i).getIs_wafat() == 0) {
		    			semuaMati = false;
		    		}
		    	}
		    	if(semuaMati) {
			    	
			    	log.info(keluarga + "kelurga ini");
			    	keluarga.setIs_tidak_berlaku("1");
			    	log.info(keluarga + "kelurga ini");
//			    	keluargaDAO.updateKeluarga(keluarga);
			    	}
			return "success-inactive";
		}
	 
	 @RequestMapping(value = "penduduk/cari")
	 public String cariPenduduk(@RequestParam(value = "kt", required = false) String kt,
			 @RequestParam(value = "kc", required = false) String kc,
			 @RequestParam(value = "kl", required = false) String kl, Model model) {
	 
		 if(kt == null && kc == null && kl == null) {
			 List<KotaModel> listKota = pendudukDAO.getAllKota();
			 model.addAttribute("listKota", listKota);
			 return "cari-kota";
		 } 
		 else if(kt != null && kc == null && kl == null) {
			 List<KecamatanModel> listKecamatan = pendudukDAO.getAllKecamatanInCity(kt);
			 KotaModel kota = pendudukDAO.getCityById(kt);
			 
			 model.addAttribute("kota", kota);
			 model.addAttribute("listKecamatan", listKecamatan);
			 return "cari-kecamatan";
		 }
		 else if(kt != null && kc != null && kl == null) {
			 List<KelurahanModel> listKelurahan = pendudukDAO.getAllKeluharanInKecamatan(kc);
			 KotaModel kota = pendudukDAO.getCityById(kt);
			 KecamatanModel kecamatan = pendudukDAO.getKecamatanById(kc);
			 
			 model.addAttribute("kota", kota);
			 model.addAttribute("kecamatan", kecamatan);
			 model.addAttribute("listKelurahan", listKelurahan);
			 return "cari-kelurahan";
		 }
		 
		 else {
			 List<PendudukModel> pendudukKelurahan = pendudukDAO.getPendudukKelurahan(kl);
			 KotaModel kota = pendudukDAO.getCityById(kt);
			 KecamatanModel kecamatan = pendudukDAO.getKecamatanById(kc);
			 KelurahanModel kelurahan = pendudukDAO.getKelurahanById(kl);
			 log.info("kelurahan " + kelurahan);
			 
			 model.addAttribute("kota", kota);
			 model.addAttribute("kecamatan", kecamatan);
			 model.addAttribute("kelurahan", kelurahan);
			 model.addAttribute("pendudukKelurahan", pendudukKelurahan);
			 return "datapenduduk-kelurahan";
		 }
	 }
		 
}
