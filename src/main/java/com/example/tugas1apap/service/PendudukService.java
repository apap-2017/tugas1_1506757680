package com.example.tugas1apap.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface PendudukService
{
	PendudukModel selectPenduduk(String nik);
	
	PendudukModel addPenduduk(PendudukModel penduduk);
	
	void updatePenduduk(PendudukModel penduduk);
	
	void pendudukIsWafat(String nik);
	
	List<PendudukModel> selectFamilyMember(String id_keluarga);
	
	KeluargaModel getFamily(String id_keluarga);
	
	KotaModel getCityById(String id_kota);
	
	KecamatanModel getKecamatanById(String id_kecamatan);
	
	KelurahanModel getKelurahanById(String id_kelurahan);
	
	List<KecamatanModel> getAllKecamatanInCity(String id_kota);
	
	List<KelurahanModel> getAllKeluharanInKecamatan(String id_kecamatan);
	
	
	List<PendudukModel> getPendudukKelurahan(String id_kelurahan);
	
	String getKodeKecamatan (String id_keluarga);
	
	String generateNIK(PendudukModel penduduk);
	
	List<KotaModel> getAllKota();
	
}
