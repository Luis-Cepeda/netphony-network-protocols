package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;


/**
 * Extended Association ID TLV
 * @author Oscar Gonzalez de Dios
 *
 */
public class SymbolicPathNameTLV extends PCEPTLV 
{
	/*
	 *  The Extended Association ID TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Extended
   Association ID TLV are as per Section 4 of [RFC6780].

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |            Length             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                Extended Association ID                      //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

              Figure 6: The Extended Association ID TLV Format

   Type:  31

   Length:  Variable.

   Extended Association ID:  As defined in Section 4 of [RFC6780].
	 */
	
	private byte[] SymbolicPathNameID;
	
	
	public SymbolicPathNameTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_SYMBOLIC_PATH_NAME);
		
	}
	
	public SymbolicPathNameTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() 
	{
		log.debug("Encoding SymbolicPathName TLV");
		this.setTLVValueLength(SymbolicPathNameID.length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		log.debug("TOTA  "+this.getTotalTLVLength()+" SW "+SymbolicPathNameID.length);
		this.encodeHeader();
		int offset=4;
		System.arraycopy(SymbolicPathNameID, 0, this.tlv_bytes, offset, SymbolicPathNameID.length);
	}

	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding SymbolicPathName TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
		
		SymbolicPathNameID = new byte[getTLVValueLength()];
		try
		{
			System.arraycopy(this.tlv_bytes, offset, SymbolicPathNameID, 0, getTLVValueLength());
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
		
		//Check if array zero
		boolean isZero = true;
		for (int i = 0; i < SymbolicPathNameID.length; i++) 
		{
			if (SymbolicPathNameID[i]!=0)
			{
				isZero = false;
				break;
			}	
		}
		
		if (isZero)
		{
			log.error("Received redundancy group identifier value can not be zero");
			//throw new MalformedPCEPObjectException();
		}
		
		//MUST be greater than 0. To check this we look at the first bit
		boolean firstBit = (ByteHandler.easyCopy(0,0,SymbolicPathNameID[0]) == 1) ? true : false ;
		if (firstBit)
		{
			log.error("Received redundancy group identifier value can not be negative");
			throw new MalformedPCEPObjectException();
		}
	}

	public byte[] getSymbolicPathNameID() 
	{
		return SymbolicPathNameID;
	}

	public void setSymbolicPathNameID(byte[] symbolicPathNameID) 
	{
		SymbolicPathNameID = symbolicPathNameID;
	}
	
	public String toString(){
		String pathName = new String (SymbolicPathNameID);
		return pathName;
	}
	
}