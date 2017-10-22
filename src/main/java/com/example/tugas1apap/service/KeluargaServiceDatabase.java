package com.example.tugas1apap.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.tugas1apap.dao.KecamatanMapper;
import com.example.tugas1apap.dao.KeluargaMapper;
import com.example.tugas1apap.dao.KelurahanMapper;
import com.example.tugas1apap.dao.KotaMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	@Autowired
	private KelurahanMapper kelurahanMapper;
	@Autowired
	private KecamatanMapper kecamatanMapper;
	@Autowired
	private KotaMapper kotaMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String nomor_kk) {
		log.info ("select citizen with nkk {}", nomor_kk);
		return keluargaMapper.selectKeluarga(nomor_kk);
	}
	
	public KeluargaModel selectKeluargaById(String id) {
		log.info ("select family with id {}", id);
		return keluargaMapper.selectKeluargaById(id);
	}

	@Override
	public List<KelurahanModel> getAllKelurahan() {
		return kelurahanMapper.getAllKelurahan();
	}
	
	@Override
	public KeluargaModel addKeluarga(KeluargaModel keluarga) {
		
		String nkk = generateNKK(keluarga);
		keluarga.setNomor_kk(nkk);
		keluarga.setIs_tidak_berlaku("0");
		keluargaMapper.addKeluarga(keluarga);
		
		return keluarga;
		
	}
	
	public String generateNKK(KeluargaModel keluarga) {
		//6 digit kode kecamatan
		String kode_kecamatan = keluargaMapper.getKodeKecamatan(keluarga.getId_kelurahan()).substring(0,6);
		log.info("kode kecamatan: " + kode_kecamatan);
		
		//6 digit tanggal
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		String tanggal = formatter.format(date);
		
		//4 digit terakhir
		String prefix = kode_kecamatan + tanggal;
		String lastNKK = keluargaMapper.getLastNKK(prefix+"%");
		
		//generate new NKK
		Long nkk = Long.parseLong(prefix + "0001");
		if(lastNKK != null) {
			nkk = Long.parseLong(lastNKK) + 1;
		}
		String nkkBaru = Long.toString(nkk);
		keluarga.setNomor_kk(nkkBaru);
		return nkkBaru;
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		 keluargaMapper.updateKeluarga(keluarga);
		
	}

	@Override
	public List<PendudukModel> selectFamilyMember(String id_keluarga) {
		return keluargaMapper.selectFamilyMember(id_keluarga);
	}

	@Override
	public List<KecamatanModel> getAllKecamatan() {
		return kecamatanMapper.getAllKecamatan();
	}

	@Override
	public List<KotaModel> getAllKota() {
		return kotaMapper.getAllKota();
	}
	
}
