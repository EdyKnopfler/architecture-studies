import StepNavigator from "../stepNavigator/StepNavigator";

export default function Flight({ destinationData, step }) {

  return (
    <>
      <StepNavigator step={step} />
      Voo para {destinationData.name} ({destinationData.id})
      -
      {step === 'going' ? ' IDA' : ' VOLTA'}
    </>
  )
}