package com.example.tugas1apap.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga (String nkk);
	
	KeluargaModel selectKeluargaById (String id);
	
	KeluargaModel addKeluarga(KeluargaModel keluarga);
	
	void updateKeluarga(KeluargaModel keluarga);
	
	List<PendudukModel> selectFamilyMember(String id_keluarga);
	
	List<KelurahanModel> getAllKelurahan();
	
	List<KecamatanModel> getAllKecamatan();
	
	List<KotaModel> getAllKota();
}
